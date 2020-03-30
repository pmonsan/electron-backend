/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentUpdateComponent } from 'app/entities/meansofpayment/meansofpayment-update.component';
import { MeansofpaymentService } from 'app/entities/meansofpayment/meansofpayment.service';
import { Meansofpayment } from 'app/shared/model/meansofpayment.model';

describe('Component Tests', () => {
  describe('Meansofpayment Management Update Component', () => {
    let comp: MeansofpaymentUpdateComponent;
    let fixture: ComponentFixture<MeansofpaymentUpdateComponent>;
    let service: MeansofpaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeansofpaymentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeansofpaymentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeansofpaymentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Meansofpayment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Meansofpayment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
