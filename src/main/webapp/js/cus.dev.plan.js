function formatterDevResult(val) {
    if (val==0){
        return "未开发";
    }else if (val==1){
        return "开发中";
    }else if (val==2){
        return "开发成功";
    }else if (val==3){
        return "开发失败";
    }else {
        return "未定义";
    }
}

function formatterOp(val,rowDate) {
    if (rowDate.devResult==0||rowDate.devResult==1){//未开发或者开发中
        var href="javascript:openCusDevPlanDetailTab('客户开发计划项管理_"+rowDate.id+"',"+rowDate.id+")";
        return "<a href = "+href+">开发</a>"
    }else if (rowDate.devResult==2||rowDate.devResult==3){//开发成功或者开发失败
        var href="javascript:openCusDevPlanDetailTab('客户开发计划项详情_"+rowDate.id+"',"+rowDate.id+")";
        return "<a href = "+href+">查看详情</a>"
    }
}

function openCusDevPlanDetailTab(title,id) {
    window.parent.openTab(title,ctx+"/cus_dev_plan/index/?id="+id);
}

$(function () {
    $("#dg").datagrid({
        rowStyler:function (index,rowDate) {
            if (rowDate.devResult==0||rowDate.devResult==1){//0 未开发 1 开发中 黄色
                return "background-color:yellow";
            }else if (rowDate.devResult==2){//成功
                return "background-color:green";
            }else if (rowDate.devResult==3){//失败
                return "background-color:red";
            }
        }
    });
});