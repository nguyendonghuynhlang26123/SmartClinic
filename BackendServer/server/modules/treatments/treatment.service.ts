import { AppointmentService } from './../appointments/appointment.service';
import { TreatmentInterface } from '../../interfaces';
import { treatmentModel } from '../../models';

const appointmentService = new AppointmentService();

export class TreatmentService {
  async getTreatmentById(treatmentId: string) {
    try {
      const treatment = await treatmentModel
        .findOne({ _id: treatmentId })
        .populate(
          'appointment prescription prescription.medicine_list.medicine'
        );
      if (!treatment) {
        throw new Error('Not Found treatment.');
      }
      return treatment;
    } catch (error) {
      console.log(error);
      throw new Error('Get treatment Error.');
    }
  }

  async createTreatment(data: TreatmentInterface) {
    appointmentService.updateAppointmentById(data.appointment, {
      status: 'PROCESSING',
    });
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const treatment = await treatmentModel.create(data);
    return treatment;
  }

  async deleteTreatment(treatmentId: string) {
    const treatment = await treatmentModel.findOne({ _id: treatmentId });
    if (!treatment) throw new Error('Not Found treatment.');
    const result = await treatmentModel
      .deleteOne({ _id: treatment._id })
      .exec();
    return result;
  }
}
