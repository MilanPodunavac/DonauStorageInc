import { IPerson } from 'app/shared/model/person.model';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';

export interface IContactInfo {
  id?: number;
  email?: string;
  phoneNumber?: string | null;
  person?: IPerson | null;
  legalEntity?: ILegalEntity | null;
}

export const defaultValue: Readonly<IContactInfo> = {};
