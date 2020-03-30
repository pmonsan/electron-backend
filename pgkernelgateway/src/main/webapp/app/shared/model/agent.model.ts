export interface IAgent {
  id?: number;
  matricule?: string;
  firstname?: string;
  lastname?: string;
  birthdate?: string;
  email?: string;
  mobilePhone?: string;
  businessEmail?: string;
  businessPhone?: string;
  address?: string;
  postalCode?: string;
  city?: string;
  countryCode?: string;
  partnerCode?: string;
  username?: string;
  active?: boolean;
  agentTypeId?: number;
}

export class Agent implements IAgent {
  constructor(
    public id?: number,
    public matricule?: string,
    public firstname?: string,
    public lastname?: string,
    public birthdate?: string,
    public email?: string,
    public mobilePhone?: string,
    public businessEmail?: string,
    public businessPhone?: string,
    public address?: string,
    public postalCode?: string,
    public city?: string,
    public countryCode?: string,
    public partnerCode?: string,
    public username?: string,
    public active?: boolean,
    public agentTypeId?: number
  ) {
    this.active = this.active || false;
  }
}
