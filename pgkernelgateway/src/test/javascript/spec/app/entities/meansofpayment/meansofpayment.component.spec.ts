/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentComponent } from 'app/entities/meansofpayment/meansofpayment.component';
import { MeansofpaymentService } from 'app/entities/meansofpayment/meansofpayment.service';
import { Meansofpayment } from 'app/shared/model/meansofpayment.model';

describe('Component Tests', () => {
  describe('Meansofpayment Management Component', () => {
    let comp: MeansofpaymentComponent;
    let fixture: ComponentFixture<MeansofpaymentComponent>;
    let service: MeansofpaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentComponent],
        providers: []
      })
        .overrideTemplate(MeansofpaymentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeansofpaymentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeansofpaymentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Meansofpayment(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.meansofpayments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
