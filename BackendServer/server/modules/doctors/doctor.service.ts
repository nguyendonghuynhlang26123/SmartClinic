import { DoctorInterface } from "../../interfaces";
import { doctorModel } from "../../models";

export class DoctorService {
  async getDoctorById(doctorId: string) {
    try {
      const doctor = await doctorModel
        .findOne({ _id: doctorId })
        .populate("hospital");
      if (!doctor) {
        throw new Error("Not Found Doctor.");
      }
      return doctor;
    } catch (error) {
      console.log(error);
      throw new Error("Get Doctor Error.");
    }
  }

  async getAllDoctor() {
    try {
      const doctors = await doctorModel.find().populate("hospital");
      return doctors;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Doctor Error.");
    }
  }

  async createDoctor(data: DoctorInterface) {
    try {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const doctor = await doctorModel.create(data);
      return doctor;
    } catch (error) {
      throw new Error("Create Doctor Error.");
    }
  }
}
