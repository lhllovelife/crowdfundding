
// 在模态框中装载Auth的属性结构数据
function fillAuthTree() {

    // 1. 发送AJAX请求查询Auth数据
    var ajaxReturn = $.ajax({
        "url" : "assign/get/all/auth.json",
        "type" : "post",
        "dataType" : "json",
        "async" : false
    });


    if (ajaxReturn.status != 200) {
        layer.msg("失败！响应状态码：" + ajaxReturn.status + " 说明信息：" + ajaxReturn.statusText);

        // 响应异常，则不需要进行下面操作
        return;
    }

    // 2. 从响应结果中获取Auth中的JSON数据
    var authList = ajaxReturn.responseJSON.data; // 该变量就是返回的auth集合

    // 3. 准备显示树形结构的json对象
    var setting = {
        "data" : {
            "simpleData" : {
                "enable" : true, //开启使用json数据（只提供用于树形结构的数据集合，不需要建立父子关系）
                "pIdKey": "categoryId"   // 使用 categoryId 属性关联父节点，不用默认的 pId了
            },
            "key": {
                // 使用 title属性显示节点名称，不用默认的 name 作为属性名了
                "name": "title"
            }
        },
        "check": {
            "enable": true // 显示checkbox
        }
    };

    // 4. 初始化树形结构
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 获取zTreeObj对象
    // 获取 zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    // console.log(zTreeObj);
    // 调用zTreeObj的方法展开所有节点
    zTreeObj.expandAll(true);

    // 5. 从响应结果获取authIdArray(该角色已经分配的权限)
    // 取出隐藏域中的roleId
    var roleId = $("#assign-roleId").val();
    ajaxReturn = $.ajax({
        "url" : "assign/get/assigned/auth/id/by/role/id.json",
        "data" : {
            "roleId" : roleId
        },
        "type" : "post",
        "dataType" : "json",
        "async" : false
    });

    if (ajaxReturn.status != 200) {
        layer.msg("失败！响应状态码：" + ajaxReturn.status + " 说明信息：" + ajaxReturn.statusText);

        // 响应异常，则不需要进行下面操作
        return;
    }
    // console.log(ajaxReturn.responseJSON);
    // 从响应结果中获取authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;

    // 6. 遍历authId数组，把树形结构对象的结点勾选上
    for (var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];

        // 根据id查询属性结构的节点
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
        var treeNode = zTreeObj.getNodeByParam("id", authId);
        // console.log(treeNode);

        // 将该节点设置为被勾选
        // checked 设置为 true 表示节点勾选
        var checked = true;
        // checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag = false;
        // 执行勾选
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    }



}


/*
success : function (response) {
    if (response.result == "SUCCESS"){

    }

    if (response.result == "FAILED"){
        layer.msg(response.message);
    }
},
error : function (response) {
    layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
}
 */