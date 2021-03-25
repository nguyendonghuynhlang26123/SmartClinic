import { HospitalInterface } from "../../interfaces";
import { hospitalModel } from "../../models";

export class HospitalService {
  async createHospital(data: HospitalInterface) {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const hospital = await hospitalModel.create(data);
      return hospital;
  }

  async getHospitalById(hospitalId: string) {
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

  async getAllHospital() {
    try {
      const hospitals = await hospitalModel.find();
      return hospitals;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Hospital Error.");
    }
  }

  async updateHospitalById(hospitalId: string, dataUpdate) {
    const hospital = await hospitalModel.findOne({ _id: hospitalId });
    if (!hospital) throw new Error("Not Found Hospital.");
    const result = await hospitalModel.updateOne(
      { _id: hospital._id },
      dataUpdate
    );
    return result;
  }

  async deleteHospital(hospitalId: string) {
    const hospital = await hospitalModel.findOne({ _id: hospitalId });
    if (!hospital) throw new Error("Not Found Hospital.");
    const result = await hospitalModel
      .deleteOne({ _id: hospital._id })
      .exec();
    return result;
  }
}
