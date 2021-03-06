import { UserInterface } from '../interfaces';
import { Document } from 'mongoose';
import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const UserSchema = new Schema(
  {
    phone: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    user_type: {
      type: String,
      required: true,
      enum: ['DOCTOR', 'PATIENT', 'NURSE'],
    },
    user_infor: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      unique: true,
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const userModel = mongoose.model<UserInterface & Document>(
  'users',
  UserSchema
);
