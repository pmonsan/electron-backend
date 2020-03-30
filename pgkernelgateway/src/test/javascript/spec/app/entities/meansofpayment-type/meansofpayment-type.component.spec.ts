/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentTypeComponent } from 'app/entities/meansofpayment-type/meansofpayment-type.component';
import { MeansofpaymentTypeService } from 'app/entities/meansofpayment-type/meansofpayment-type.service';
import { MeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

describe('Component Tests', () => {
  describe('MeansofpaymentType Management Component', () => {
    let comp: MeansofpaymentTypeComponent;
    let fixture: ComponentFixture<MeansofpaymentTypeComponent>;
    let service: MeansofpaymentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentTypeComponent],
        providers: []
      })
        .overrideTemplate(MeansofpaymentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeansofpaymentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeansofpaymentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MeansofpaymentType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.meansofpaymentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
