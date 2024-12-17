package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.ApplicantDTO;
import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.DTOs.OrderPickDTO;
import com.graduationproject.DTOs.SearchOrderDTO;
import com.graduationproject.entities.Order;
import com.graduationproject.entities.OrderApplicants;
import com.graduationproject.entities.User;
import com.graduationproject.mapper.OrderMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {
    private ModelMapper modelMapper;

    public OrderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SearchOrderDTO orderToSearchOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        // Use ModelMapper to map fields
        SearchOrderDTO searchOrderDTO = modelMapper.map(order, SearchOrderDTO.class);

        // Set additional fields that ModelMapper might not handle
        if (order.getUser() != null) {
            searchOrderDTO.setUserId(order.getUser().getId());
        }

        return searchOrderDTO;
    }

    @Override
    public Order searchOrderDTOToOrder(SearchOrderDTO searchOrderDTO) {
        if (searchOrderDTO == null) {
            return null;
        }

        // Use ModelMapper to map back to the Order entity
        return modelMapper.map(searchOrderDTO, Order.class);
    }

    @Override
    public OrderApplicants mapToOrderApplicants(OrderPickDTO dto, Order order, User commuter) {
        OrderApplicants applicants = new OrderApplicants();
        applicants.setOrder(order);
        applicants.setCommuter(commuter);
        applicants.setApllicantPrice(dto.getApllicantPrice());
        return applicants;
    }

    @Override
    public ApplicantDTO mapToApplicantDTO(OrderApplicants orderApplicants) {
        ApplicantDTO dto = new ApplicantDTO();
        dto.setCommuterId(orderApplicants.getCommuter().getId());
        dto.setFullName(orderApplicants.getCommuter().getFullName());
        dto.setPrice(orderApplicants.getApllicantPrice());
        dto.setCommuterPhotoURL(orderApplicants.getCommuter().getProfilePictureUrl());
        return dto;
    }

    @Override
    public Order mapToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        return modelMapper.map(orderDTO, Order.class);
    }

    @Override
    public OrderDTO mapToOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        return modelMapper.map(order, OrderDTO.class);
    }
}