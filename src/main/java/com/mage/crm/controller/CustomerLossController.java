package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.service.CusomerLossService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController{
    @Resource
    private CusomerLossService cusomerLossService;
    @RequestMapping("index")
    public String index(){
        return "customer_loss";
    }
    @RequestMapping("queryCustomerLossesByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery){
        return cusomerLossService.queryCustomerLossesByParams(customerLossQuery);
    }

    @RequestMapping("toRepreivePage/{lossId}")
    public String toRepreivePage(@PathVariable("lossId") Integer lossId, Model model){
        model.addAttribute("customerLoss",cusomerLossService.queryCustomerLossesById(lossId));
        return "customer_repri";
    }

    @RequestMapping("updateCustomerLossState")
    @ResponseBody
    public MessageModel updateCustomerLossState(Integer lossId,String lossReason){
        cusomerLossService.updateCustomerLossState(lossId,lossReason);
        return createMessageModel("流失操作执行成功");
    }
}
