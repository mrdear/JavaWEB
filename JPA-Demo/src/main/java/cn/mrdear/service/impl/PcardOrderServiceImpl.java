package cn.mrdear.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.mrdear.repository.PcardOrderRepository;
import cn.mrdear.service.PcardOrderService;

/**
 * @author Niu Li
 * @date 2016/11/18
 */
@Service
public class PcardOrderServiceImpl implements PcardOrderService {

    @Resource
    private PcardOrderRepository pcardOrderRepository;


}
