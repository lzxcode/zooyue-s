package zooyue.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录密码账号")

public class UserLoginDTO  implements Serializable {
    @ApiModelProperty("用户名")

  private String userName;
    @ApiModelProperty("密码")

  private String password;
}
