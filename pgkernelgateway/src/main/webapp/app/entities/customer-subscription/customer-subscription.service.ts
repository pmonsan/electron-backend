import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';

type EntityResponseType = HttpResponse<ICustomerSubscription>;
type EntityArrayResponseType = HttpResponse<ICustomerSubscription[]>;

@Injectable({ providedIn: 'root' })
export class CustomerSubscriptionService {
  public resourceUrl = SERVER_API_URL + 'api/customer-subscriptions';

  constructor(protected http: HttpClient) {}

  create(customerSubscription: ICustomerSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerSubscription);
    return this.http
      .post<ICustomerSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerSubscription: ICustomerSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerSubscription);
    return this.http
      .put<ICustomerSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerSubscription: ICustomerSubscription): ICustomerSubscription {
    const copy: ICustomerSubscription = Object.assign({}, customerSubscription, {
      creationDate:
        customerSubscription.creationDate != null && customerSubscription.creationDate.isValid()
          ? customerSubscription.creationDate.toJSON()
          : null,
      modificationDate:
        customerSubscription.modificationDate != null && customerSubscription.modificationDate.isValid()
          ? customerSubscription.modificationDate.toJSON()
          : null,
      validationDate:
        customerSubscription.validationDate != null && customerSubscription.validationDate.isValid()
          ? customerSubscription.validationDate.toJSON()
          : null,
      startDate:
        customerSubscription.startDate != null && customerSubscription.startDate.isValid() ? customerSubscription.startDate.toJSON() : null,
      endDate: customerSubscription.endDate != null && customerSubscription.endDate.isValid() ? customerSubscription.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
      res.body.modificationDate = res.body.modificationDate != null ? moment(res.body.modificationDate) : null;
      res.body.validationDate = res.body.validationDate != null ? moment(res.body.validationDate) : null;
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customerSubscription: ICustomerSubscription) => {
        customerSubscription.creationDate = customerSubscription.creationDate != null ? moment(customerSubscription.creationDate) : null;
        customerSubscription.modificationDate =
          customerSubscription.modificationDate != null ? moment(customerSubscription.modificationDate) : null;
        customerSubscription.validationDate =
          customerSubscription.validationDate != null ? moment(customerSubscription.validationDate) : null;
        customerSubscription.startDate = customerSubscription.startDate != null ? moment(customerSubscription.startDate) : null;
        customerSubscription.endDate = customerSubscription.endDate != null ? moment(customerSubscription.endDate) : null;
      });
    }
    return res;
  }
}
