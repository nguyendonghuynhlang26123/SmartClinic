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

  async getAllServices(query?) {
    try {
      let selection = {};
      if (query?.select) {
        if (query.select instanceof Array) {
          for (const property of query.select) {
            selection[property] = 1;
          }
        } else selection = { [query.select]: 1 };
      }

      const services = await medicalServiceModel.find(
        {
          service_name: new RegExp(query.search, 'i'),
        },
        selection,
        {
          limit: Number(query.limit),
        }
      );
      return services;
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
