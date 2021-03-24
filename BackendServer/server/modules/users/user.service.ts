import { UserInterface } from '../../interfaces';
import { userModel } from '../../models';
import * as bcrypt from 'bcrypt';

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

  async findUserByPhone(userPhone: string) {
    const user = await userModel.findOne({ phone: userPhone });
    return user;
  }

  async createUser(data: UserInterface) {
    if (!data?.phone || !data?.password) throw new Error('Empty data');

    const user = await this.findUserByPhone(data.phone);
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
