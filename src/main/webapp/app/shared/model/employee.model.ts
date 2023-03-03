import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IPerson } from 'app/shared/model/person.model';
import { IUser } from 'app/shared/model/user.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IEmployee {
  id?: number;
  uniqueIdentificationNumber?: string;
  birthDate?: string;
  disability?: boolean;
  employment?: boolean | null;
  profilePictureContentType?: string | null;
  profilePicture?: string | null;
  address?: IAddress;
  personalInfo?: IPerson;
  user?: IUser;
  company?: ICompany;
}

export const defaultValue: Readonly<IEmployee> = {
  disability: false,
  employment: false,
};
