package com.ceng316.ceng316_oims_backend.IztechUser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IztechUserService {

    private final IztechUserRepository iztechUserRepository;

    public IztechUser login(IztechUser iztechUserCredentials) {
        Optional<IztechUser> optional_iztech_user = Optional.empty();
        if (iztechUserCredentials.getEmail() != null) {
            optional_iztech_user = iztechUserRepository.findByEmail(iztechUserCredentials.getEmail());
        } else {
            optional_iztech_user = iztechUserRepository.findById(iztechUserCredentials.getId());
        }

        if (optional_iztech_user.isPresent() && optional_iztech_user.get().getPassword().equals(iztechUserCredentials.getPassword())) {
            IztechUser iztechUserInfo = optional_iztech_user.get();
            return new IztechUser(iztechUserInfo.getId(), iztechUserInfo.getFullName(), iztechUserInfo.getEmail(), iztechUserInfo.getRole(),
                    iztechUserInfo.getIdentityNumber(), iztechUserInfo.getSchoolId(), iztechUserInfo.getGrade(), iztechUserInfo.getContactNumber());
        }

        return null;
    }
}
