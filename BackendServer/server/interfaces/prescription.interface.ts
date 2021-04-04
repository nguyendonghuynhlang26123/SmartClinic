import { BaseInterface } from './base/base.interface';

export class PrescriptionDetails {
  medicine: string;
  quantity: number;
  note: string;
}

export class PrescriptionInterface extends BaseInterface {
  medicine_list: PrescriptionDetails[];
  doctor: string;
  patient: string;
  symptoms: string;
  diagnosis: string;
  note: string;
}
