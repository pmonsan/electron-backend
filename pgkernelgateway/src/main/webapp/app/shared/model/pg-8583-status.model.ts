export interface IPg8583Status {
  id?: number;
  status?: string;
  defaultReason?: string;
}

export class Pg8583Status implements IPg8583Status {
  constructor(public id?: number, public status?: string, public defaultReason?: string) {}
}
