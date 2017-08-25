<%
var package = apiObjMap.mPackageName;
var baseUrl = apiObjMap.mUrlGetterName;
var postType = apiObjMap.mPostClassName;
var fileMap = apiObjMap.mFileInfos;

%>
package ${package};

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有网络请求
 *
 * @author mili
 * @createTime 2016-10-02 上午10:01:00
 */
public class RequestBase{

<%
for(var apiInfo in apiObjMap.mApiInfos)
// var apiInfo = apiObjMap.mApiInfos[2];
{
    var comment = apiInfo.dataParamDesc;
    var action = apiInfo.action;
    var medthodName = apiInfo.name;
    var files = fileMap[action];
    
    // 注释
    var functionComment = {
        for(var paramInfo in apiInfo.dataParams){
            
            var format = "\n     * @param {0} {1}";
            print(strutil.format(format, paramInfo.mName, paramInfo.mDes));
        }
    };
    // 文件注释
    var fileComment = {
        if(files != null){
            for(var fileInfo in files){
                
                var format = "\n     * @param {0} {1}";
                print(strutil.format(format, fileInfo.mArrayName, fileInfo.mDes));
            }
        }
    };
    
    // 参数
    var functionParams = {
        for(var paramInfo in apiInfo.dataParams){
            
            print('String ' + paramInfo.mName + ', ');
        }
    };
    // 文件参数
    var fileParams = {
        if(files != null){
            for(var fileInfo in files){
                
                // 若是数组
                if(fileInfo.mIsArray){
                    print('List<String> ' + fileInfo.mArrayName + ', ');
                }
                // 若不是数组
                else{
                    print('String ' + fileInfo.mArrayName + ', ');
                }
            }
        }
    };
%>
    /**
     * ${comment}
     * ${functionComment}${fileComment}
     * @param listener 网络回调事件
     */
    public static void get${medthodName}(${functionParams}${fileParams}Post.FullListener listener) {

        // 网址
        String url = HostUtil.splicelHost(${baseUrl}, "${action}");
        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("UserID", ApplicationData.getInstance().getLoginUserId()));
        params.add(new Post.ParamNameValuePair("Session", ApplicationData.getInstance().getLoginSession()));
        <%
        for(var paramInfo in apiInfo.mParams){
            
            var format = "        params.add(new Post.ParamNameValuePair(\"{0}\", {1}));";
            println(strutil.format(format, paramInfo.mName, paramInfo.mName));
        }
        %>
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        <%
        if(files != null){
            for(var fileInfo in files){
                
                // 若是数组
                if(fileInfo.mIsArray){
        %>
        for (String file : ${fileInfo.mArrayName}) {
            fileParams.add(new Post.ParamNameValuePair("${fileInfo.mArrayName}", file));
        }
        <%
                }
                
                // 若不是数组
                else{
                    var format = "        params.add(new Post.ParamNameValuePair(\"{0}\", {1}));";
                    println(strutil.format(format, fileInfo.mArrayName, fileInfo.mArrayName));
                }
            }
        }
        %>

        ${postType} post = new ${postType}(url);
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }
<%
// 方法循环 结束
}
%>
}
