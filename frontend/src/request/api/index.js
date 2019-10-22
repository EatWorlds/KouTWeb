/** 
 * 功能：api接口的统一出口
 */

// 注册模块接口
import registered from '@/request/api/registered';
import logins from '@/request/api/login';

// 导出接口
export default {    
    registered,
    logins,
}
