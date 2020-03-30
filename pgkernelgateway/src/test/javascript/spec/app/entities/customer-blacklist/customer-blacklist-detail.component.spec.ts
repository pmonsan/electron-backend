/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerBlacklistDetailComponent } from 'app/entities/customer-blacklist/customer-blacklist-detail.component';
import { CustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

describe('Component Tests', () => {
  describe('CustomerBlacklist Management Detail Component', () => {
    let comp: CustomerBlacklistDetailComponent;
    let fixture: ComponentFixture<CustomerBlacklistDetailComponent>;
    const route = ({ data: of({ customerBlacklist: new CustomerBlacklist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerBlacklistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerBlacklistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerBlacklistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerBlacklist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
