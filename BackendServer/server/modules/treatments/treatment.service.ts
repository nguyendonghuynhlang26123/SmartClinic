import { AppointmentService } from './../appointments/appointment.service';
import { TreatmentInterface } from '../../interfaces';
import { treatmentModel } from '../../models';

const appointmentService = new AppointmentService();

export class TreatmentService {
  async getTreatments() {
    const treatments = await treatmentModel
      .find()
      .populate('appointment prescription prescription.medicine_list.medicine');
    if (!treatments) {
      throw new Error('Get treatment error');
    }
    return treatments;
  }

  async getTreatmentById(treatmentId: string) {
    const treatment = await treatmentModel
      .findOne({ _id: treatmentId })
      .populate('appointment prescription prescription.medicine_list.medicine');
    if (!treatment) {
      throw new Error('Not Found treatment.');
    }
    return treatment;
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
