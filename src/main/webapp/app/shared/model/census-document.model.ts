import dayjs from 'dayjs';
import { ICensusItem } from 'app/shared/model/census-item.model';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IStorage } from 'app/shared/model/storage.model';
import { CensusDocumentStatus } from 'app/shared/model/enumerations/census-document-status.model';

export interface ICensusDocument {
  id?: number;
  censusDate?: string;
  status?: CensusDocumentStatus | null;
  accountingDate?: string | null;
  leveling?: boolean | null;
  censusItems?: ICensusItem[] | null;
  businessYear?: IBusinessYear;
  president?: IEmployee;
  deputy?: IEmployee;
  censusTaker?: IEmployee;
  storage?: IStorage;
}

export const defaultValue: Readonly<ICensusDocument> = {
  leveling: false,
};
