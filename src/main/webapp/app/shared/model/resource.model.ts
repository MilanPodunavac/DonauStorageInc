import { ICensusItem } from 'app/shared/model/census-item.model';
import { ITransferDocumentItem } from 'app/shared/model/transfer-document-item.model';
import { IMeasurementUnit } from 'app/shared/model/measurement-unit.model';
import { ICompany } from 'app/shared/model/company.model';
import { ResourceType } from 'app/shared/model/enumerations/resource-type.model';

export interface IResource {
  id?: number;
  name?: string;
  type?: ResourceType;
  censusItems?: ICensusItem[] | null;
  transferDocumentItems?: ITransferDocumentItem[] | null;
  unit?: IMeasurementUnit;
  company?: ICompany;
}

export const defaultValue: Readonly<IResource> = {};
