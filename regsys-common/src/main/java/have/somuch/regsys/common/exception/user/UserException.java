package have.somuch.regsys.common.exception.user;

import have.somuch.regsys.common.exception.BaseException;

/**
 * 用户异常处理类
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
