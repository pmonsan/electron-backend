/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';

describe('Service Tests', () => {
  describe('Customer Service', () => {
    let injector: TestBed;
    let service: CustomerService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomer;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CustomerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Customer(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Customer', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Customer(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Customer', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            corporateName: 'BBBBBB',
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            gpsLatitude: 1,
            gpsLongitude: 1,
            homePhone: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            workPhone: 'BBBBBB',
            otherQuestion: 'BBBBBB',
            responseOfQuestion: 'BBBBBB',
            tradeRegister: 'BBBBBB',
            address: 'BBBBBB',
            postalCode: 'BBBBBB',
            city: 'BBBBBB',
            email: 'BBBBBB',
            countryCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            activityAreaCode: 'BBBBBB',
            customerTypeCode: 'BBBBBB',
            questionCode: 'BBBBBB',
            username: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Customer', async () => {
        const returnedFromService = Object.assign(
          {
            number: 'BBBBBB',
            corporateName: 'BBBBBB',
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            gpsLatitude: 1,
            gpsLongitude: 1,
            homePhone: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            workPhone: 'BBBBBB',
            otherQuestion: 'BBBBBB',
            responseOfQuestion: 'BBBBBB',
            tradeRegister: 'BBBBBB',
            address: 'BBBBBB',
            postalCode: 'BBBBBB',
            city: 'BBBBBB',
            email: 'BBBBBB',
            countryCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            activityAreaCode: 'BBBBBB',
            customerTypeCode: 'BBBBBB',
            questionCode: 'BBBBBB',
            username: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Customer', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
