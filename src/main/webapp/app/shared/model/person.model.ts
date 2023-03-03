import { IContactInfo } from 'app/shared/model/contact-info.model';
import { IBusinessContact } from 'app/shared/model/business-contact.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPerson {
  id?: number;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  maidenName?: string | null;
  gender?: Gender;
  contactInfo?: IContactInfo;
  businessContact?: IBusinessContact | null;
  employee?: IEmployee | null;
}

export const defaultValue: Readonly<IPerson> = {};
