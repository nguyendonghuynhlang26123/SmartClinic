import { HospitalInterface } from "../../interfaces";
import { hospitalModel } from "../../models";

export class HospitalService {
  async createHospital(data: HospitalInterface) {
    try {
      const hospital = await hospitalModel.create(data);
      return hospital;
    } catch (error) {
      console.log(error);
      throw new Error("Create Hospital Error.");
    }
  }

  async getHospitalById(hospitalId: string): Promise<HospitalInterface> {
    try {
      const hospital = await hospitalModel.findOne({ _id: hospitalId });
      if (!hospital) {
        throw new Error("Not Found Hospital.");
      }
      return hospital;
    } catch (error) {
      console.log(error);
      throw new Error("Get Hospital Error.");
    }
  }

  async getAllHospital(): Promise<HospitalInterface[]> {
    try {
      const hospitals = await hospitalModel.find();
      return hospitals;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Hospital Error.");
    }
  }
}
