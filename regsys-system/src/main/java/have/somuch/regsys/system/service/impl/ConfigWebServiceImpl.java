package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Config;
import have.somuch.regsys.system.entity.ConfigData;
import have.somuch.regsys.system.mapper.ConfigDataMapper;
import have.somuch.regsys.system.mapper.ConfigMapper;
import have.somuch.regsys.system.service.IConfigWebService;
import have.somuch.regsys.system.vo.configweb.ConfigDataInfoVo;
import have.somuch.regsys.system.vo.configweb.ConfigInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigWebServiceImpl implements IConfigWebService {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ConfigDataMapper configDataMapper;

    /**
     * 获取配置列表
     *
     * @return
     */
    @Override
    public JsonResult getList() {
        List<Config> configList = configMapper.selectList(new LambdaQueryWrapper<Config>()
                .eq(Config::getMark, 1)
                .orderByAsc(Config::getSort));
        List<ConfigInfoVo> configInfoVoList = new ArrayList<>();
        if (!configList.isEmpty()) {
            for (Config config : configList) {
                ConfigInfoVo configInfoVo = new ConfigInfoVo();
                configInfoVo.setConfigId(config.getId());
                configInfoVo.setConfigName(config.getName());
                // 获取配置项
                List<ConfigData> configDataList = configDataMapper.selectList(new LambdaQueryWrapper<ConfigData>()
                        .eq(ConfigData::getConfigId, config.getId())
                        .eq(ConfigData::getMark, 1)
                        .orderByAsc(ConfigData::getSort));
                List<ConfigDataInfoVo> configDataInfoVoList = new ArrayList<>();
                if (!configDataList.isEmpty()) {
                    configDataList.forEach(item -> {
                        ConfigDataInfoVo configDataInfoVo = new ConfigDataInfoVo();
                        BeanUtils.copyProperties(item, configDataInfoVo);
                        if (item.getType().equals("array") || item.getType().equals("radio") || item.getType().equals("checkbox") || item.getType().equals("select")) {
                            if (StringUtils.isNotEmpty(item.getOptions())) {
                                String[] options = item.getOptions().split(",");
                                if (options.length > 0) {
                                    Map<Integer, String> map = new HashMap<>();
                                    for (String option : options) {
                                        String[] strings1 = option.split("=");
                                        map.put(Integer.valueOf(strings1[0]), strings1[1]);
                                    }
                                    configDataInfoVo.setParam(map);
                                }
                            }
                            // 复选框
                            if (item.getType().equals("checkbox")) {
                                String[] strings1 = item.getValue().split(",");
                                configDataInfoVo.setValueList(Arrays.asList(strings1));
                            }
                        }
                        // 单图
                        if (item.getType().equals("image") && StringUtils.isNotEmpty(item.getValue())) {
                            configDataInfoVo.setValue(CommonUtils.getImageURL(item.getValue()));
                        }
                        // 多图
                        if (item.getType().equals("images") && StringUtils.isNotEmpty(item.getValue())) {
                            String[] strings1 = item.getValue().split(",");
                            List<String> stringList = new ArrayList<>();
                            for (String s : strings1) {
                                stringList.add(CommonUtils.getImageURL(s));
                            }
                            configDataInfoVo.setValueList(stringList);
                        }
                        configDataInfoVoList.add(configDataInfoVo);
                    });
                }
                configInfoVo.setDataList(configDataInfoVoList);
                configInfoVoList.add(configInfoVo);
            }
        }
        return JsonResult.success(configInfoVoList);
    }

    /**
     * 保存表单信息
     *
     * @param info 表单信息
     * @return
     */
    @Override
    public JsonResult edit(Map<String, Object> info) {
        if (StringUtils.isNull(info)) {
            return JsonResult.error("表单信息不能为空");
        }
        for (String key : info.keySet()) {
            Object obj = info.get(key);
            if (StringUtils.isNull(obj)) {
                continue;
            }
            String value = "";
            if (obj instanceof List) {
                List<String> stringList = new ArrayList<>();
                for (Object val : ((ArrayList) obj)) {
                    // 图片处理
                    if (val.toString().contains(CommonConfig.imageURL)) {
                        stringList.add(val.toString().replaceAll(CommonConfig.imageURL, ""));
                    } else {
                        stringList.add(val.toString());
                    }
                }
                value = StringUtils.join(stringList, ",");
            } else if (obj.toString().contains("http://") || obj.toString().contains("https://")) {
                // 图片处理
                if (!StringUtils.isEmpty(obj.toString()) && obj.toString().contains(CommonConfig.imageURL)) {
                    value = obj.toString().replaceAll(CommonConfig.imageURL, "");
                } else {
                    value = obj.toString();
                }
            } else {
                value = StringUtils.isNull(obj) ? "" : obj.toString();
            }
            ConfigData configData = configDataMapper.selectOne(new LambdaQueryWrapper<ConfigData>()
                    .eq(ConfigData::getCode, key)
                    .last("limit 1"));
            if (StringUtils.isNull(configData)) {
                continue;
            }
            ConfigData configDataItem = new ConfigData();
            configDataItem.setId(configData.getId());
            configDataItem.setValue(value);
            configDataMapper.updateById(configDataItem);
        }
        return JsonResult.success("保存成功");
    }
}
