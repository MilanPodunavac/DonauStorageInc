import { IContactInfo } from 'app/shared/model/contact-info.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPerson {
  id?: number;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  maidenName?: string | null;
  gender?: Gender;
  contactInfo?: IContactInfo;
}

export const defaultValue: Readonly<IPerson> = {};
