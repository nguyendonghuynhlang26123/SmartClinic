import { MedicineInterface } from "../../interfaces";
import { medicineModel } from "../../models";

export class MedicineService {
  async getMedicineById(medicineId: string) {
    try {
      const medicine = await medicineModel
        .findOne({ _id: medicineId })
        .populate("category");
      if (!medicine) {
        throw new Error("Not Found Medicine.");
      }
      return medicine;
    } catch (error) {
      console.log(error);
      throw new Error("Get Medicine Error.");
    }
  }

  async getAllMedicine() {
    try {
      const medicines = await medicineModel.find().populate("category");
      return medicines;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Medicine Error.");
    }
  }

  async createMedicine(data: MedicineInterface) {
    try {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const medicine = await medicineModel.create(data);
      return medicine;
    } catch (error) {
      throw new Error("Create Medicine Error.");
    }
  }
}
