import { DoctorInterface } from '../../interfaces';
import { doctorModel } from '../../models';

export class DoctorService {
  async getDoctorById(doctorId: string) {
    const doctor = await doctorModel
      .findOne({ _id: doctorId })
      .populate('hospital');
    if (!doctor) {
      throw new Error('Not Found Doctor.');
    }
    return doctor;
  }

  async getAllDoctor(query?) {
    let filter = {};
    if (query?.service) filter = { specialty_services: query.service };

    console.log(
      'log ~ file: doctor.service.ts ~ line 16 ~ DoctorService ~ getAllDoctor ~ query',
      filter
    );
    const doctors = await doctorModel.find(filter).populate('hospital');
    return doctors;
  }

  async createDoctor(data: DoctorInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const doctor = await doctorModel.create(data);
    return doctor;
  }

  async updateDoctorById(doctorId: string, dataUpdate) {
    const doctor = await doctorModel.findOne({ _id: doctorId });
    if (!doctor) throw new Error('Not Found Doctor.');
    const result = await doctorModel.updateOne({ _id: doctor._id }, dataUpdate);
    return result;
  }

  async deleteDoctor(doctorId: string) {
    const doctor = await doctorModel.findOne({ _id: doctorId });
    if (!doctor) throw new Error('Not Found Doctor.');
    const result = await doctorModel.deleteOne({ _id: doctor._id }).exec();
    return result;
  }
}
