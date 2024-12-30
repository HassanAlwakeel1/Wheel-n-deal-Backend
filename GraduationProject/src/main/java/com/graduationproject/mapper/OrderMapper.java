package com.graduationproject.mapper;

import com.graduationproject.DTOs.ApplicantDTO;
import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.DTOs.OrderPickDTO;
import com.graduationproject.DTOs.SearchOrderDTO;
import com.graduationproject.entities.Order;
import com.graduationproject.entities.OrderApplicants;
import com.graduationproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    SearchOrderDTO orderToSearchOrderDTO(Order order);
    Order searchOrderDTOToOrder(SearchOrderDTO searchOrderDTO);
    OrderApplicants mapToOrderApplicants(OrderPickDTO dto, Order order, User commuter);
    ApplicantDTO mapToApplicantDTO(OrderApplicants orderApplicants);
    Order mapToOrder(OrderDTO orderDTO);
    OrderDTO mapToOrderDTO(Order order);
}
