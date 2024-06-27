package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.mapper.StaffAdminMapper;
import com.example.backend.core.admin.repository.StaffAdminRepository;
import com.example.backend.core.admin.service.StaffAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffAdminServiceImpl implements StaffAdminService {
    @Autowired
    private StaffAdminRepository staffAdminRepository;

    private ServiceResult<StaffAdminDTO> result = new ServiceResult<>();

    @Autowired
    private StaffAdminMapper staffMapper;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<StaffAdminDTO> getAllStaff() {
        List<Staff> listStaff = staffAdminRepository.findAll();
        List<StaffAdminDTO> listDto = new ArrayList<>();
        return listDto = staffMapper.toDto(listStaff);
    }

    @Override
    public ServiceResult<StaffAdminDTO> findById(Long id) {
        Optional<Staff> optional = this.staffAdminRepository.findById(id);
        if (optional.isPresent()) {
            Staff staff = optional.get();
            StaffAdminDTO staffAdminDTO = staffMapper.toDto(staff);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Tìm thấy nhân viên thành công");
            result.setData(staffAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy Sole với id " + id);
        }
        return result;
    }

    @Override
    public ServiceResult<StaffAdminDTO> updateStaff(Long id, StaffAdminDTO staffAdminDTO) {
        Optional<Staff> staffOptional = staffAdminRepository.findById(id);
        ServiceResult<StaffAdminDTO> result = new ServiceResult<>();

        if (!staffOptional.isPresent()) {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Staff not found");
            result.setData(null);
            return result;
        }

        Staff staff = staffOptional.get();

        if (staffAdminDTO.getPhone() != null && !staffAdminDTO.getPhone().equals(staff.getPhone())) {
            if (staffAdminRepository.existsByPhone(staffAdminDTO.getPhone())) {
                result.setStatus(HttpStatus.OK);
                result.setMessage("Phone existed");
                result.setData(staffMapper.toDto(staff));
                return result;
            } else {
                staff.setPhone(staffAdminDTO.getPhone());
            }
        }

        if (staffAdminDTO.getEmail() != null && !staffAdminDTO.getEmail().equals(staff.getEmail())) {
            if (staffAdminRepository.existsByEmail(staffAdminDTO.getEmail())) {
                result.setStatus(HttpStatus.OK);
                result.setMessage("Email existed");
                result.setData(staffMapper.toDto(staff));
                return result;
            } else {
                staff.setEmail(staffAdminDTO.getEmail());
            }
        }

        staff.setFullname(staffAdminDTO.getFullname());
        staff.setPhone(staffAdminDTO.getPhone());
        staff.setEmail(staffAdminDTO.getEmail());
        staff.setBirthday(staffAdminDTO.getBirthday());
        staff.setGender(staffAdminDTO.getGender());

        if (staffAdminDTO.getPasswword() != null) {
            staff.setPassword(encoder.encode(staffAdminDTO.getPasswword()));
        }

        staff.setIdel(staffAdminDTO.getIdel());
        staff.setRole(staffAdminDTO.getRole());
        staff.setDescription(staffAdminDTO.getDescription());

        staffAdminRepository.save(staff);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Update successful");
        result.setData(staffMapper.toDto(staff));

        return result;
    }


//    @Override
//    public ServiceResult<StaffAdminDTO> updateStaff(Long id,StaffAdminDTO staffAdminDTO) {
//        Optional<Staff> staffOptional = staffAdminRepository.findById(id);
//
//        if (staff.getPhone() != null && !staff.getPhone().equals(staffAdminDTO.getPhone())) {
//            if (staffAdminRepository.existsByPhone(staffAdminDTO.getPhone())) {
//                result.setStatus(HttpStatus.OK);
//                result.setMessage("Phone existed");
//                result.setData(staffMapper.toDto(staff));
//                return result;
//            }
//        } else {
//            staff.setPhone(staffAdminDTO.getPhone());
//        }
//
//        if (staff.getEmail() != null && !staff.getEmail().equals(staffAdminDTO.getEmail())) {
//            if (staffAdminRepository.existsByEmail(staffAdminDTO.getEmail())) {
//                result.setStatus(HttpStatus.OK);
//                result.setMessage("Email existed");
//                result.setData(staffMapper.toDto(staff));
//                return result;
//            }
//        } else {
//            staff.setEmail(staffAdminDTO.getEmail());
//        }
//
//        if (!staff.equals("")) {
//            staff.setFullname(staffAdminDTO.getFullname());
//            staff.setPhone(staffAdminDTO.getPhone());
//            staff.setEmail(staffAdminDTO.getEmail());
//            staff.setBirthday(staffAdminDTO.getBirthday());
//            staff.setGender(staffAdminDTO.getGender());
//            if (staffAdminDTO.getPasswword() != null) {
//                staff.setPassword(encoder.encode(staffAdminDTO.getPasswword()));
//            }
//            staff.setIdel(staffAdminDTO.getIdel());
//            staff.setRole(staffAdminDTO.getRole());
//            staff.setDescription(staffAdminDTO.getDescription());
//            this.staffAdminRepository.save(staff);
//            result.setStatus(HttpStatus.OK);
//            result.setMessage("Sua thanh cong");
//            result.setData(staffMapper.toDto(staff));
//        } else {
//            result.setStatus(HttpStatus.BAD_REQUEST);
//            result.setMessage("Sua khong thanh cong");
//            result.setData(null);
//        }
//        return result;
//    }

    @Override
    public List<StaffAdminDTO> findByCodeOrPhone(String param) {
        if (!param.equals("")) {
            List<StaffAdminDTO> staffAdminDTOList = staffMapper.toDto(staffAdminRepository.findByCodeLikeOrPhoneLike("%" + param + "%", "%" + param + "%"));
            return staffAdminDTOList;
        } else {
            return this.getAllStaff();
        }
    }

    @Override
    public List<StaffAdminDTO> findByFullnameOrPhoneLike(String params) {
        if (!params.equals("")) {
            List<StaffAdminDTO> staffAdminDTOList = staffMapper.toDto(staffAdminRepository.findByFullnameOrPhoneLike("%" + params + "%", "%" + params + "%"));
            return staffAdminDTOList;
        } else {
            return this.getAllStaff();
        }
    }
}
