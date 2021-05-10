import { ObjectId } from 'mongoose';
import { BaseInterface } from './base/base.interface';

export class AnswerInterface {
  content: string;
  user_type: 'DOCTOR' | 'PATIENT' | 'NURSE';
  display_name: string;
  user_id: ObjectId;
}
export class ForumInterface extends BaseInterface {
  topic: string;
  answers: AnswerInterface[];
}
