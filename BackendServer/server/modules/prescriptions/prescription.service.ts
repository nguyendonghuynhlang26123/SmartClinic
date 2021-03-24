import { PrescriptionInterface } from "../../interfaces";
import { prescriptionModel } from "../../models";

export class PrescriptionService {
  async getPrescriptionById(prescriptionId: string) {
    try {
      const prescription = await prescriptionModel
        .findOne({ _id: prescriptionId })
        .populate("medicine_list.medicine");
      if (!prescription) {
        throw new Error("Not Found Prescription.");
      }
      return prescription;
    } catch (error) {
      console.log(error);
      throw new Error("Get Prescription Error.");
    }
  }

  async getAllPrescription() {
    try {
      const prescriptions = await prescriptionModel
        .find()
        .populate("medicine_list.medicine");
      return prescriptions;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Prescription Error.");
    }
  }

  async createPrescription(data: PrescriptionInterface) {
    try {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const prescription = await prescriptionModel.create(data);
      return prescription;
    } catch (error) {
      throw new Error("Create Prescription Error.");
    }
  }
}
