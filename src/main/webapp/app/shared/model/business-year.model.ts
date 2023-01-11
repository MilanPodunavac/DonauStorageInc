import { ICompany } from 'app/shared/model/company.model';

export interface IBusinessYear {
  id?: number;
  yearCode?: string;
  completed?: boolean;
  company?: ICompany;
}

export const defaultValue: Readonly<IBusinessYear> = {
  completed: false,
};
