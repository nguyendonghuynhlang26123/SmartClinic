import { AppointmentService } from './../appointments/appointment.service';
import {
  PrescriptionInterface,
  TreatmentRegisterDTO,
  TreatmentInterface,
  AppointmentInterface,
} from '../../interfaces';
import {
  appointmentModel,
  patientModel,
  prescriptionModel,
  treatmentModel,
} from '../../models';
import { Status } from '../../common';

const appointmentService = new AppointmentService();
let ObjectId = require('mongoose').Types.ObjectId;
export class TreatmentService {
  async getTreatments(query?) {
    let filter = {};
    let selection = {};
    let sortBy = {};
    let nestedPopulate = null;
    if (query?.sortBy && query?.sortType)
      sortBy = { [query.sortType]: query.sortBy };
    else sortBy = { created_at: -1 };
    if (query?.date) filter = { ...filter, date: query.date };
    if (query?.service_id)
      filter = { ...filter, service: new ObjectId(query.service_id) };
    if (query?.patient_id)
      filter = { ...filter, patient: new ObjectId(query.patient_id) };
    if (query?.doctor_id)
      filter = { ...filter, patient: new ObjectId(query.doctor_id) };
    if (query?.select) {
      if (query.select instanceof Array) {
        for (const s of query.select) {
          selection[s] = 1;
        }
      } else selection = { [query.select]: 1 };
    }
    if (
      query?.populate &&
      query.populate.includes('prescription.medicine_list.medicine')
    )
      nestedPopulate = {
        path: 'prescription',
        populate: {
          path: 'medicine_list.medicine',
          model: 'medicines',
          select: 'medicine_name',
        },
      };

    const treatments = await treatmentModel
      .find({ ...filter }, selection, {
        limit: Number(query?.limit),
        populate: query.populate ?? '',
      })
      .populate(nestedPopulate ?? '')
      .sort(sortBy);
    if (!treatments) {
      throw new Error('Get treatment error');
    }
    return treatments;
  }

  async getTreatmentById(treatmentId: string) {
    const treatment = await treatmentModel
      .findOne({ _id: treatmentId })
      .populate({
        path: 'prescription',
        populate: {
          path: 'medicine_list.medicine',
          model: 'medicines',
          select: 'medicine_name',
        },
      });
    if (!treatment) {
      throw new Error('Not Found treatment.');
    }
    return treatment;
  }

  async createTreatment(data: TreatmentInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const treatment = await treatmentModel.create(data);
    return treatment;
  }

  async registerTreatment(data: TreatmentRegisterDTO) {
    //Check appointment exists
    let appointment = await appointmentModel.findOne({ _id: data.appointment });
    if (!appointment) throw new Error('Cannot find Appointment');
    let appointmentData: any = appointment.toObject();

    const prescriptionData: PrescriptionInterface = {
      symptoms: data.symptoms,
      diagnosis: data.diagnosis,
      note: data.prescription_note,
      medicine_list: data.medicine_list,
    };

    const prescription = await prescriptionModel.create(prescriptionData);
    if (!prescription) throw new Error('Cannot create Prescription');

    const treatmentData: TreatmentInterface = {
      doctor: data.doctor,
      patient: appointmentData.patient,
      service: appointmentData.service,
      time: appointmentData.time,
      date: appointmentData.date,
      note: appointmentData.note,
      prescription: prescription.id,
    };
    const treatment = await treatmentModel.create(treatmentData);

    await patientModel.updateOne(
      { _id: appointmentData.patient },
      { current_appointment: null }
    );
    await appointmentModel.updateOne(
      { _id: data.appointment },
      { status: Status.COMPLETED }
    );

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
