import { BaseInterface } from './base/base.interface';

export class TreatmentInterface extends BaseInterface {
  doctor: string;
  patient: string;
  service: string;
  time: string;
  date: string;
  note: string;
  prescription: string;
}

export class TreatmentRegisterDTO {
  appointment: string;
  doctor: string;

  medicine_list: [
    {
      medicine: string;
      quantity: number;
      note: string;
    }
  ];
  symptoms: string;
  diagnosis: string;
  prescription_note: string;
}
