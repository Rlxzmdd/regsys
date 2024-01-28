### 目录结构记录
- entity 实体类目录  
目前存放College、Major、Classes、Department类
- user 用户相关类目录   
从逻辑上来说，UserWechat中将会有字段绑定到UserStudent和UserTeacher中，所以将其单独放在一个包下，方便管理。
  - UserWechat
    - UserStudent
    - UserTeacher
- common 存放一些工具类
- shiro 存放shiro相关类
