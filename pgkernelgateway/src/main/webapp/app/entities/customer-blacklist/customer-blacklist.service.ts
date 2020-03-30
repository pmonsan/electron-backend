import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

type EntityResponseType = HttpResponse<ICustomerBlacklist>;
type EntityArrayResponseType = HttpResponse<ICustomerBlacklist[]>;

@Injectable({ providedIn: 'root' })
export class CustomerBlacklistService {
  public resourceUrl = SERVER_API_URL + 'api/customer-blacklists';

  constructor(protected http: HttpClient) {}

  create(customerBlacklist: ICustomerBlacklist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerBlacklist);
    return this.http
      .post<ICustomerBlacklist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerBlacklist: ICustomerBlacklist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerBlacklist);
    return this.http
      .put<ICustomerBlacklist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerBlacklist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerBlacklist[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerBlacklist: ICustomerBlacklist): ICustomerBlacklist {
    const copy: ICustomerBlacklist = Object.assign({}, customerBlacklist, {
      insertionDate:
        customerBlacklist.insertionDate != null && customerBlacklist.insertionDate.isValid()
          ? customerBlacklist.insertionDate.toJSON()
          : null,
      modificationDate:
        customerBlacklist.modificationDate != null && customerBlacklist.modificationDate.isValid()
          ? customerBlacklist.modificationDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.insertionDate = res.body.insertionDate != null ? moment(res.body.insertionDate) : null;
      res.body.modificationDate = res.body.modificationDate != null ? moment(res.body.modificationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customerBlacklist: ICustomerBlacklist) => {
        customerBlacklist.insertionDate = customerBlacklist.insertionDate != null ? moment(customerBlacklist.insertionDate) : null;
        customerBlacklist.modificationDate = customerBlacklist.modificationDate != null ? moment(customerBlacklist.modificationDate) : null;
      });
    }
    return res;
  }
}
