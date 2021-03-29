import { MedicalServiceInterface } from '../../interfaces';
import { medicalServiceModel } from '../../models';

export class MedicalServicesService {
  async getById(id: string) {
    const service = await medicalServiceModel.findOne({ _id: id });
    if (!service) {
      throw new Error('Not Found Service.');
    }
    return service;
  }

  async getAllPatient() {
    try {
      const patients = await medicalServiceModel.find();
      return patients;
    } catch (error) {
      console.log(error);
      throw new Error('Get All Services Error.');
    }
  }

  async createAService(data: MedicalServiceInterface) {
    return await medicalServiceModel.create(data);
  }

  async updateServiceById(id: string, dataUpdate: MedicalServiceInterface) {
    const service = await medicalServiceModel.findOne({ _id: id });
    if (!service) throw new Error('Not Found Patient.');
    const result = await medicalServiceModel.updateOne({ _id: id }, dataUpdate);
    return result;
  }

  async deleteService(id: string) {
    const patient = await medicalServiceModel.findOne({ _id: id });
    if (!patient) throw new Error('Not Found Patient.');
    const result = await medicalServiceModel.deleteOne({ _id: id }).exec();
    return result;
  }
}
