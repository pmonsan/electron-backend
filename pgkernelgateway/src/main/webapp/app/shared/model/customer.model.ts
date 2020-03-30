export interface ICustomer {
  id?: number;
  number?: string;
  corporateName?: string;
  firstname?: string;
  lastname?: string;
  gpsLatitude?: number;
  gpsLongitude?: number;
  homePhone?: string;
  mobilePhone?: string;
  workPhone?: string;
  otherQuestion?: string;
  responseOfQuestion?: string;
  tradeRegister?: string;
  address?: string;
  postalCode?: string;
  city?: string;
  email?: string;
  countryCode?: string;
  partnerCode?: string;
  activityAreaCode?: string;
  customerTypeCode?: string;
  questionCode?: string;
  username?: string;
  active?: boolean;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public number?: string,
    public corporateName?: string,
    public firstname?: string,
    public lastname?: string,
    public gpsLatitude?: number,
    public gpsLongitude?: number,
    public homePhone?: string,
    public mobilePhone?: string,
    public workPhone?: string,
    public otherQuestion?: string,
    public responseOfQuestion?: string,
    public tradeRegister?: string,
    public address?: string,
    public postalCode?: string,
    public city?: string,
    public email?: string,
    public countryCode?: string,
    public partnerCode?: string,
    public activityAreaCode?: string,
    public customerTypeCode?: string,
    public questionCode?: string,
    public username?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
