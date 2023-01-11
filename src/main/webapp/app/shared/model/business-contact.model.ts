import { IPerson } from 'app/shared/model/person.model';

export interface IBusinessContact {
  id?: number;
  personalInfo?: IPerson;
}

export const defaultValue: Readonly<IBusinessContact> = {};
