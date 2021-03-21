import { userModel } from "../../models";

export class UserService {
  async getUserById(userId: string) {
    //: Promise<UserInterface>
    try {
      const user = await userModel.findOne({ _id: userId });
      if (!user) {
        throw new Error("Not Found User.");
      }
      return user;
    } catch (error) {
      console.log(error);
      throw new Error("Get User Error.");
    }
  }

  async getAllUser() {
    //: Promise<UserInterface[]>
    try {
      const users = await userModel.find();
      return users;
    } catch (error) {
      console.log(error);
      throw new Error("Get All User Error.");
    }
  }
}
