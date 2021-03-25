import { PrescriptionInterface } from "../../interfaces";
import { prescriptionModel } from "../../models";

export class PrescriptionService {
  async getPrescriptionById(prescriptionId: string) {
    try {
      const prescription = await prescriptionModel
        .findOne({ _id: prescriptionId })
        .populate("medicine_list.medicine doctor patient");
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
        .populate("medicine_list.medicine doctor patient");
      return prescriptions;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Prescription Error.");
    }
  }

  async createPrescription(data: PrescriptionInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const prescription = await prescriptionModel.create(data);
    return prescription;
  }

  async updatePrescriptionById(prescriptionId: string, dataUpdate) {
    const prescription = await prescriptionModel.findOne({ _id: prescriptionId });
    if (!prescription) throw new Error("Not Found Prescription.");
    const result = await prescriptionModel.updateOne(
      { _id: prescription._id },
      dataUpdate
    );
    return result;
  }

  async deletePrescription(prescriptionId: string) {
    const prescription = await prescriptionModel.findOne({ _id: prescriptionId });
    if (!prescription) throw new Error("Not Found Prescription.");
    const result = await prescriptionModel
      .deleteOne({ _id: prescription._id })
      .exec();
    return result;
  }
}
