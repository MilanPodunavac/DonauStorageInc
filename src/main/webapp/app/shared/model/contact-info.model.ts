export interface IContactInfo {
  id?: number;
  email?: string;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<IContactInfo> = {};
