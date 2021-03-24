import { UserInterface } from '../../interfaces';
import { userModel } from '../../models';

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
}
