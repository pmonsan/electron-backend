/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerTypeDetailComponent } from 'app/entities/customer-type/customer-type-detail.component';
import { CustomerType } from 'app/shared/model/customer-type.model';

describe('Component Tests', () => {
  describe('CustomerType Management Detail Component', () => {
    let comp: CustomerTypeDetailComponent;
    let fixture: ComponentFixture<CustomerTypeDetailComponent>;
    const route = ({ data: of({ customerType: new CustomerType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
