function generateTree() {
    // 1. ajax获取整个树结构的数据
    $.ajax({
        url : "menu/get/whole/tree.json",
        type : "post",
        dataType : "json",
        success : function (response) {
            if (response.result == "SUCCESS"){
                // 响应成功
                var zNodes = response.data;
                // 2. 创建Json对象用于存储对zTree所做的设置
                var setting = {
                    "view": {
                        "addDiyDom" : addDiyDom,
                        "addHoverDom" : addHoverDom,
                        "removeHoverDom" : removeHoverDom
                    },
                    "data": {
                        "key": {
                            "url": "maomi"
                        }
                    }
                };
                // 3. 初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }

            if (response.result == "FAILED"){
                layer.msg(response.message);
            }
        },
        error : function (response) {
            layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
        }
    });
}


// 用于在节点上固定显示用户自定义控件(每生成一个结点的时候会调用一次)
// treeId: 是整个树形结构附着的ul标签的id（treeDemo）
// treeNode: 当前树形结构节点的全部数据，包括从后端查询到的Menu对象的全部数据
// 函数功能：修改默认图标为真是图标
function addDiyDom(treeId, treeNode) {
    // console.log(treeId);
    // console.log(treeNode);

    // 控制图标的span标签（每个节点的span标签都是有一定规则的）
    var spanid = treeNode.tId + "_ico";
    $("#"+spanid).removeClass().addClass(treeNode.icon);
}

// 鼠标离开结点范围时删除按钮组
function removeHoverDom(treeId, treeNode) {
    // 拼接按钮组的id
    var btnGroupId = treeNode.tId + "_btnGrp";
    // 移除对应的元素
    $("#"+btnGroupId).remove();
}
// 鼠标移入结点范围添加按钮组
function addHoverDom(treeId, treeNode) {
    // 按钮组出现的位置：trDemo_num_a超链接的后面
    var aId = treeNode.tId + "_a";
    var btnGroupId = treeNode.tId + "_btnGrp";

    //判断一下，是否已经有生成按钮组
    var num = $("#"+btnGroupId).length;
    // console.log(num)
    if (num > 0) {
        return;
    }

    // 准备各个按钮的HTML标签
    var addBtn = "<a id='"+treeNode.id+"' href='javascript:void(0);' class='addBtn btn btn-info dropdown-toggle btn-xs' onclick='addNode(this.id)' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' href='javascript:void(0);'  class='removeBtn btn btn-info dropdown-toggle btn-xs' onclick='removeNode(this.id)' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' href='javascript:void(0);' class='editBtn btn btn-info dropdown-toggle btn-xs' onclick='editNode(this.id)' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    // 获取当前节点的级别
    var level = treeNode.level;

    // 存储拼装好的按钮html代码
    var btnHtml = "";

    if (level == 0) {
        // 级别为0时候，只能添加
        btnHtml = addBtn;
    }

    if (level == 1) {
        // 级别为1时候，可以添加和修改
        btnHtml = addBtn + " " + editBtn;
        // 如果该节点有子节点，不能删除；没有则能删除
        var length = treeNode.children.length;

        if (length == 0) {
            btnHtml += removeBtn;
        }
    }

    if (level == 2) {
        // 叶子结点可以修改、删除
        btnHtml = editBtn + " " + removeBtn;
    }


    $("#"+aId).after("<span id='" + btnGroupId  + "'>"+btnHtml+"</span>");
}

// 为当前节点添加子节点
function addNode(id) {
    // alert("添加节点" + id);
    // 将当前节点的id设置到隐藏域中
    $("#currentNodeId").val(id);

    // 打开添加的模态窗口
    $("#menuAddModal").modal("show");


    return false;
}
// 修改当前节点
function editNode(id) {
    // 获取 zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

    var key = "id";
    var value = id;
    var currentNode = zTreeObj.getNodeByParam(key, value);

    // 将当前节点id设置到隐藏域
    $("#edit-currentNodeId").val(id);
    // 回显数据
    $("#edit-name").val(currentNode.name);
    $("#edit-url").val(currentNode.url);
    // 回显 radio 可以这样理解：被选中的 radio 的 value 属性可以组成一个数组，
    // 然后再用这个数组设置回 radio，就能够把对应的值选中
    $("#menuEditModal [name=icon]").val([currentNode.icon]);

    // 打开更新菜单模态窗口
    $("#menuEditModal").modal("show");

    return false;
}
// 删除当前节点
function removeNode(id) {

    // 将当前节点id设置到隐藏域中
    $("#remove-id").val(id);

    // 获取当前节点名字
    // 获取 zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var currentNode = zTreeObj.getNodeByParam("id", id);
    var name = currentNode.name;
    // 将要删除信息显示在模态框上
    $("#removeNodeSpan").html("【 <i class='"+currentNode.icon+"'></i> &nbsp;&nbsp;"+currentNode.name+"】");
    // 打开删除确认模态窗口
    $("#menuConfirmModal").modal("show");

    return false;
}