import { BaseInterface } from './base/base.interface';

export class PatientInterface extends BaseInterface {
  patient_name: string;
  patient_avatar: string;
  patient_gender?: 'Male' | 'Female';
  patient_dob?: number;
  patient_weight?: number;
  current_appointment?: string;
  token: string;
}
