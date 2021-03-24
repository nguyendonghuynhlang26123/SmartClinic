import { UserInterface } from '../../interfaces';
import { userModel } from '../../models';
import * as bcrypt from 'bcrypt';
import { PatientService } from '../patients/patient.service';

async function hashPassword(password) {
  const saltRounds = 10;
  const hash = await bcrypt.hashSync(password, saltRounds);
  return hash;
}
export class UserService {
  async getUserById(userId: string) {
    try {
      const user = await userModel.findOne({ _id: userId }, { password: 0 });
      if (!user) {
        throw new Error('Not Found User.');
      }
      return user;
    } catch (error) {
      console.log(error);
      throw new Error('Get User Error.');
    }
  }

  async getAllUser() {
    try {
      const users = await userModel.find({}, { password: 0 });
      return users;
    } catch (error) {
      console.log(error);
      throw new Error('Get All User Error.');
    }
  }

  //Return true if phone number is registered
  async checkExisted(phone: string) {
    let user = await userModel.findOne({ phone: phone });
    return user !== null;
  }

  async findUserByPhone(userPhone: string) {
    let user = await userModel.findOne({ phone: userPhone });
    if (!user) throw new Error('Not found user');
    const data = await user.toObject();
    const patientService = new PatientService();
    const patient = await patientService.getPatientById(user.user_infor);
    if (!patient) throw new Error('Not found patient information');

    return {
      ...data,
      user_infor: patient,
    };
  }

  async createUser(data: UserInterface) {
    if (!data?.phone || !data?.password) throw new Error('Empty data');

    const user = await userModel.findOne({ phone: data.phone });
    if (user) throw new Error('Registed Phone number');

    let password = await hashPassword(data.password);
    const userData = {
      phone: data.phone,
      password: password,
      user_type: data.user_type || 'PATIENT',
      user_infor: data.user_infor,
    };
    return await userModel.create(userData);
  }
}
