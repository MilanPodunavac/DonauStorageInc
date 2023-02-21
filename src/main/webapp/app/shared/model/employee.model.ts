import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IPerson } from 'app/shared/model/person.model';
import { ICompany } from 'app/shared/model/company.model';
import { IUser } from 'app/shared/model/user.model';

export interface IEmployee {
  id?: number;
  uniqueIdentificationNumber?: string;
  birthDate?: string;
  disability?: boolean;
  employment?: boolean | null;
  address?: IAddress;
  personalInfo?: IPerson;
  company?: ICompany;
  user?: IUser;
}

export const defaultValue: Readonly<IEmployee> = {
  disability: false,
  employment: false,
};
